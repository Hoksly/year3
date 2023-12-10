#include <iostream>
#include <type_traits>
#include "compfunc.hpp"

namespace os::lab1::compfunc {
    comp_result<unsigned> compfunc(int );
}

int main()
{
    std::cout << "f(0): " << std::flush << os::lab1::compfunc::compfunc(0) << std::endl;
    auto result = os::lab1::compfunc::compfunc(3);
    std::cout << "f(3)  ";
    while (std::holds_alternative<os::lab1::compfunc::soft_fault>(result)) {
	std::cout << "." << std::flush;
	result = os::lab1::compfunc::compfunc(3);
    }
    std::cout << "\rf(3): " << result << std::endl;
}
