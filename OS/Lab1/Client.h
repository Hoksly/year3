#include <iostream>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <string>

#include <iostream>
#include <csignal>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/un.h>

#include <csignal>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <fcntl.h>


#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <sys/types.h>
#include <optional>

#include <chrono>
#include <thread>

#include <random>

#include "include/compfunc.hpp"

namespace os::lab1::compfunc {
    comp_result<unsigned> compfunc(int );
}


class Client {
private:
    const char* socketPath = "mysockettest"; // Adjust the path as needed
    std::string funcType;
    int value;

    os::lab1::compfunc::comp_result<unsigned> result;

    int sock = 0;

public:
    Client(const std::string& funcType) : funcType(funcType) {
    
    }

    void compute()
    {
        readMessage();
        std::optional<int> computedResult = std::nullopt;
        
        std::cout << "Client:" + funcType + " Computing with value: " << value << std::endl;
        try
        {
            result = os::lab1::compfunc::compfunc(value);
        }
        catch(const std::exception& e)
        {
            std::cerr << e.what() << '\n';
        }
        
      
        
        std::cout << "Client:" + funcType + " Computed " << std::endl;
       

        

        std::string resultString;
        if(std::holds_alternative<unsigned>(result))
        {
            resultString = std::to_string(std::get<unsigned>(result));
        }
        else if(std::holds_alternative<os::lab1::compfunc::soft_fault>(result))
        {
            resultString = "soft_fault";
        }
        else if(std::holds_alternative<os::lab1::compfunc::hard_fault>(result))
        {
            resultString = "hard_fault";
        }
        resultString = funcType + resultString;
        
        sendMessage(resultString);
    }

    void sendMessage(std::string result)
    {
        try 
        {
            int sock = 0;
            int data_len = 0;
            struct sockaddr_un remote;
            char send_msg[100];

            if( (sock = socket(AF_UNIX, SOCK_STREAM, 0)) == -1  )
            {
                printf("Client: Error on socket() call \n");
                return;
            }

            remote.sun_family = AF_UNIX;
            strcpy( remote.sun_path, socketPath );
            data_len = strlen(remote.sun_path) + sizeof(remote.sun_family);

            printf("Client: Trying to connect... \n");
            if( connect(sock, (struct sockaddr*)&remote, data_len) == -1 )
            {
                std::cout << "Client " +  funcType + " Error on connect call send \n" << std::endl;
                return;
            }

    

            int data_recv = 0;
           
            memset(send_msg, 0, 100*sizeof(char));
            strcpy(send_msg, result.c_str());
            if( send(sock, send_msg, strlen(send_msg)*sizeof(char), 0 ) == -1 )
            {
                printf("Client: Error on send() call \n");
            }

         
            
            
        }
        catch (const std::exception& e)
        {
            std::cout << e.what() << std::endl;
        }

        std::cout << "Client " + funcType + " Exiting sending " << std::endl;
        close(sock);
    }

    void readMessage()
    {
        try 
        {
            int sock = 0;
            int data_len = 0;
            struct sockaddr_un remote;
            char *recv_msg = new char[200];
            char *send_msg = new char[100];

            memset(recv_msg, 0, 200*sizeof(char));
            memset(send_msg, 0, 100*sizeof(char));

            if( (sock = socket(AF_UNIX, SOCK_STREAM, 0)) == -1  )
            {
                printf("Client: Error on socket() call \n");
                return;
            }

            remote.sun_family = AF_UNIX;
            strcpy( remote.sun_path, socketPath );
            data_len = strlen(remote.sun_path) + sizeof(remote.sun_family);

            printf("Client: Trying to connect... \n");
            if( connect(sock, (struct sockaddr*)&remote, data_len) == -1 )
            {
                printf("Client: Error on connect call read \n");
                return;
            }

            

            int data_recv = 0;
           
            memset(recv_msg, 0, 200*sizeof(char));
            memset(send_msg, 0, 100*sizeof(char));
            while (true)
            {
                data_recv = recv(sock, recv_msg, 200, 0);
                std::cout << "Client " + funcType + " waiting for message" << std::endl;   
            
                if(data_recv > 0)
                {
                    std::cout << "Client " + funcType +" Received message: " << std::string(recv_msg) << std::endl;
                    
                    
                    
                    value = std::stoi(recv_msg);
                      
                    break;
                }
                std::this_thread::sleep_for(std::chrono::seconds(1));   
            }
            
            delete recv_msg;
            delete send_msg;
        }
        catch (const std::exception& e)
        {
            std::cout << e.what() << std::endl;
        }

        std::cout << "Client " + funcType + " Exiting reading " << std::endl;
       

        close(sock);
       
    }    

};