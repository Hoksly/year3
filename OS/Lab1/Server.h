#include <iostream>
#include <unistd.h>
#include <thread>
#include <csignal>
#include <iostream>
#include <sys/socket.h>
#include <netinet/in.h>
#include <sys/un.h>
#include <future>
#include <chrono>

#include "Client.h"

#include "include/compfunc.hpp"




class Manager
{
  private:
    int value;
    int result;

    bool cancel = false, failF = false, failG = false;

    std::thread threadF, threadG;
    const char* socketPath = "mysockettest";

    std::future<void> futureF, futureG;    

    std::chrono::time_point<std::chrono::system_clock> startTime;

    int serverSocket;
    public: 

    static Manager* instancePtr;
    std::vector<int> results;

  public: 
    Manager(int inputValue)
    {
      std::cout << "Manager created" << std::endl;
      this->value = inputValue;
      instancePtr = this;

    std::signal(SIGINT, handleSignal);

    }
    void safeStop()
    {
        std::cout << "Safe stop" << std::endl;
        close(serverSocket);

        exit(0);

    }
    void run()
    {
        initSocketServer(socketPath);
        std::cout << "Server initialized" << std::endl;
        std::cout << serverSocket << std::endl;
        if (serverSocket == -1) {
            return;
        }

        Client clientF("f"), clientG("g");
        
        std::cout << "Clients created" << std::endl;

        futureF = std::async(std::launch::async, [&clientF]() {
        clientF.compute();
        });
        
        futureG = std::async(std::launch::async, [&clientG]() {
            clientG.compute();
        });
        
        std::cout << "F and G started" << std::endl;
        
        std::future<void> futureSend = std::async(std::launch::async, &Manager::sendValues, this);

         checkProcesses();
        

    }

    

    private:

    void checkProcesses()
    {
        std::atomic<bool> fAlive, gAlive;
        fAlive = true;
        gAlive = true;

        startTime = std::chrono::system_clock::now();

        int counter = 0;
        
        std::future<void> futureRead = std::async(std::launch::async, &Manager::readData, this); 
        
        
        while(true)
        {
            std::cout << "Checking processes" << std::endl;
            if(fAlive && futureF.wait_for(std::chrono::seconds(0)) == std::future_status::ready)
            {
                
                fAlive = false;
                counter++;
                if(counter == 2)
                {
                    std::cout << "Break 1" << std::endl;
                    break;  
                }
            }
            else if(cancel)
            {
                std::cout << "Break 2" << std::endl;
                break;
            }

            if(gAlive && futureG.wait_for(std::chrono::seconds(0)) == std::future_status::ready)
            {
                
                gAlive = false;
                counter++;
                if(counter == 2)
                {
                    std::cout << "Break 3" << std::endl;
                    break;  
                }
            }
            else if(cancel)
            {
                std::cout << "Break 4" << std::endl;
                break;
            }

            if(std::chrono::duration_cast<std::chrono::seconds>(std::chrono::system_clock::now() - startTime).count() > 5)
            {
                std::cout << "Time is up, computation canceled" << std::endl;
                safeStop();
                std::cout << "Break 5" << std::endl;
                break;
            }
            std::this_thread::sleep_for(std::chrono::seconds(1));
        }

        if(results.size() == 2)
        {
            std::cout << "Result: " << results[0] + results[1] << std::endl;
        }

        else
        {
            std::cout << "Result is undefined" << std::endl;
        }
        
    }
    




    void initSocketServer(const char* socketPath)
    {
        this->serverSocket = socket(AF_UNIX, SOCK_STREAM, 0);
        if (serverSocket == -1) {
            std::cerr << "Failed to create socket." << std::endl;
            return;
        }

        sockaddr_un serverAddress{};
        serverAddress.sun_family = AF_UNIX;
        strncpy(serverAddress.sun_path, socketPath, sizeof(serverAddress.sun_path) - 1);

        if (bind(serverSocket, (struct sockaddr*)&serverAddress, sizeof(serverAddress)) == -1) {
            std::cerr << "Failed to bind socket." << std::endl;
            close(serverSocket);
            return;
        }

        if (listen(serverSocket, 5) == -1) {
            std::cerr << "Failed to listen on socket." << std::endl;
            close(serverSocket);
            return;
        }

        std::cout << "Server listening on Unix socket: " << socketPath << std::endl;

    }



    void sendValues()
    {
        for(int i = 0; i < 2; ++i)
        {
            std::cout << "Sending value" << std::endl;
            int clientSocket = accept(serverSocket, nullptr, nullptr);
            if (clientSocket == -1) {
                std::cerr << "Failed to accept connection on socket." << std::endl;
                close(serverSocket);
                return;
            }
            std::cout << "Client connected" << std::endl;
            std::string result = std::to_string(value);
            if (send(clientSocket, result.c_str(), strlen(result.c_str())*sizeof(char), 0 ) == -1 )
            {
                std::cerr << "Error on send() call" << std::endl;
            }
            close(clientSocket);
        }
      
    }
    void parseData(std::string message)
    {
        std::cout << "Parsing data" << std::endl;
        std::cout << message << std::endl;
        
        std::string funcType = message.substr(0, 1);
        std::string valueString = message.substr(1, message.length());
        
        std::cout << "Function type: " << funcType << std::endl;
        std::cout << "Value: " << valueString << std::endl;
        
        if(valueString == "hard_fault")
        {
            if(funcType == "f")
            {
                failF = true;
            }
            else if(funcType == "g")
            {
                failG = true;
            }
            std::cout << "Hard fault on function " << funcType << std::endl;
            cancel = true;
            safeStop();
        }
        else if (valueString == "soft_fault")
        {
            std::cout << "Soft fault on function " << funcType << std::endl;
        }

        else
        {   
            if(valueString == "")
            {
                std::cout << "No value received from " << funcType << std::endl;
                return;
            }

            int result = std::stoi(valueString);
            results.push_back(result);
            std::cout << "Result "+ funcType + ": " << result << std::endl;
            std::cout << "Results size: " << results.size() << std::endl;
        }
        
        
    }
    void readData()
    {
        std::cout << "Reading data" << std::endl;
        for(int i = 0; i < 2; ++i)
        {
            int clientSocket = accept(serverSocket, nullptr, nullptr);
            if (clientSocket == -1) {
                    std::cerr << "Failed to accept connection on socket." << std::endl;
                    close(serverSocket);
                    return;
            }
            std::cout << "Client connected" << std::endl;
            char *buffer = new char[100];
            memset(buffer, 0, 100*sizeof(char));
            if(recv(clientSocket, buffer, 100*sizeof(char), 0) == -1)
            {
                std::cout << "Error on recv() call" << std::endl;
            }
            std::cout << buffer << std::endl;
         
            close(clientSocket);
            std::string message(buffer);
            delete[] buffer;
            parseData(message);
        }

    }

    static void handleSignal(int signal)
    {
        if(signal == SIGINT)
        {
            std::cout << "Interruption received" << std::endl;
            startCanceling();
        }
    }

    static void startCanceling()
    {
        
            std::cout << "Canceling..." << std::endl;
        
            instancePtr->cancel = true;
            instancePtr->safeStop();
            
        
    }

};

Manager* Manager::instancePtr = nullptr;