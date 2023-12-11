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
    const char* socketPath = "socks.sock"; // Adjust the path as needed
    std::string funcType;
    int value;

    os::lab1::compfunc::comp_result<unsigned> result;

public:
    Client(const std::string& funcType) : funcType(funcType) {
        compute();
    }

    void compute()
    {
        readMessage();
         std::optional<int> computedResult = std::nullopt;

         int randomF, randomG;
         // c++ random number generation

        std::random_device rd;
        std::mt19937 gen(rd());

    // Define a distribution (e.g., uniform distribution for integers between 1 and 100)
        std::uniform_int_distribution<int> dis(1, 15);

        randomF = dis(gen);
        randomG = dis(gen);

        try 
         {
            // Thread sleep
            std::this_thread::sleep_for(std::chrono::seconds(8));
         }
        catch (const std::exception& e)
        {
                std::cout << e.what() << std::endl;
        }

        if(randomF < 10)
        {
            if (this->funcType == "F")
            {
                result = os::lab1::compfunc::compfunc(randomF);
            }
        }

        if (randomG < 10)
        {
            if (this->funcType == "G")
            {
                result = os::lab1::compfunc::compfunc(randomG);
            }
        }

        else if(randomG < 12)
        {
            while(true)
            {
                try{
                    std::this_thread::sleep_for(std::chrono::seconds(1));
                }
                catch (const std::exception& e)
                {
                    std::cout << e.what() << std::endl;
                }
            }
        }

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
            int s2 = 0;
            struct sockaddr_un remote;
            int len = 0;

            sock = socket(AF_UNIX, SOCK_STREAM, 0);
            if( -1 == sock )
            {
                printf("Error on socket() call \n");
                return;
            }

            remote.sun_family = AF_UNIX;
            strcpy( remote.sun_path, socketPath );
            
            len = strlen(remote.sun_path) + sizeof(remote.sun_family);
            if( connect(sock, (struct sockaddr*)&remote, len) == -1 )
            {
                printf("Error on connect call \n");
                return;
            }

            if( send(sock, result.c_str(), strlen(result.c_str())*sizeof(char), 0 ) == -1 )
            {
                printf("Error on send() call \n");
            }
            close(sock);
        }
        catch (const std::exception& e)
        {
            std::cout << e.what() << std::endl;
        }
    }

    void readMessage()
    {
        try 
        {
            int sock = 0;
            int data_len = 0;
            struct sockaddr_un remote;
            char recv_msg[200];
            char send_msg[100];

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
                printf("Client: Error on connect call \n");
                return;
            }

            printf("Client: Connected \n");

            while( printf(">"), fgets(send_msg, 100, stdin), !feof(stdin))
            {
                if( send(sock, send_msg, strlen(send_msg)*sizeof(char), 0 ) == -1 )
                {
                    printf("Client: Error on send() call \n");
                }
                memset(send_msg, 0, 100*sizeof(char));
                memset(recv_msg, 0, 200*sizeof(char));

                if( (data_len = recv(sock, recv_msg, 200, 0)) > 0 )
                {
                    printf("Client: Data received: %s \n", recv_msg);
                    value = atoi(recv_msg);
                }
                else
                {
                    if(data_len < 0)
                    {
                        printf("Client: Error on recv() call \n");
                    }
                    else
                    {
                        printf("Client: Server closed connection \n");
                    }
                    break;
                }
            }
            close(sock);
        }
        catch (const std::exception& e)
        {
            std::cout << e.what() << std::endl;
        }
       
    }    

};


int main() {
    // Example usage
    Client client("F");
    return 0;
}
