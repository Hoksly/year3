def task1
  res = 0
  factorial = 2.0

  (2..10).each { |i|
    res += ((factorial / (i - 1)) / (factorial * (i + 1))) ** (i * (i + 1))
    factorial *= (i + 1)
  }

  res
end

def task2(x)
  res = 0
  factorial = 1

  (0..10).each{ |i|
    res += (x.to_f ** i) / factorial
    factorial *= (i + 1)
  }

  res
end

def factorial(n)
  res = 1
  (1..n).each{ |i|
    res *= i
  }
  res.to_f
end


def task3
  res = 0.0

  (1..10).each{ |i|
    iter = factorial(4 * (i -1)) 
    iter *= (factorial(2* (i - 1))) 
    iter /= factorial(3 * i) 
    iter /= factorial(i) 
    iter /= 3 ** (2*i)
    res += iter

  }

  res
end

print task1.to_s + "\n"
print Math.exp(1).to_s + " " + task2(1).to_s + "\n"
print task3.to_s + "\n"