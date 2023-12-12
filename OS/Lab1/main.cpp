#include <iostream>
#include <future>
#include <chrono>
#include <thread>
#include "Server.h"


int myFunction() {
    std::this_thread::sleep_for(std::chrono::seconds(3));
    return 42;
}

int main() {

  int value;
  std::cout << "Enter x: ";
  std::cin >> value;
  std::cout << "You entered: " << value << std::endl;
  Manager manager(value);
  manager.run();
}
