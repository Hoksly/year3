#Variant 8

def integrate_rectangle(a, b, n, dx=(b - a)/n)
  x = a + dx / 2
  sum = 0
  (1..n).each do
    y = yield(x)
    # print x, "\t", y, "\n"
    sum += dx * y
    x += dx
  end
  sum
end

def integrate_trapezoid(a, b, n, dx=(b-a)/n)
  x = a + dx
  sum = dx * (yield(a) / 2 - yield(b) / 2)
  loop do
    y = yield(x)
    # print y, "\n"
    sum += dx * y
    x += dx
    break if x > b
  end
  sum
end

def f1(x)
  (x**2 - 1) * 10 ** (-2 * x)
end

def f2(x)
  x * Math.atan(x)
end

def series_sum(x, n)
  x_left_bound = 0.1
  x_right_bound = 0.8
  n_left_bound = 14
  n_right_bound = 58
  if x < x_left_bound || x > x_right_bound
    raise "X is out of bounds"
  end

  sum = 0
  if n >= n_left_bound && n <= n_right_bound
    (0..n).each do |i|
      sum += yield(x, i)
    end
  else
    e = 0.001
    fault = e + 1
    prev_sum = sum
    i = 0
    while i < 100 || fault > e
      sum += yield(x, i)
      # print sum, "\n"

      i += 1
      fault = (prev_sum - sum).abs
      prev_sum = sum
      # print fault, "\n"
    end
  end

  sum
end

def series_f(x, i)
  Math.sin(((2 * i - 1) * x).to_i) / (2 * i - 1)
end

z = integrate_rectangle(0.0, 0.4, 100000.0) {|x| f1(x)}
puts "Rectangle 1: ", z, "\n" # -0.17698
z = integrate_trapezoid(0.0, 0.4, 100000.0) {|x| f1(x)}
puts "Trapezoid 1: ", z, "\n" # -0.17698

z = integrate_rectangle(0.0, 1.7, 100000.0) {|x| f2(x)}
puts "Rectangle 2: ", z, "\n" # 1.170995544
z = integrate_trapezoid(0.0, 1.7, 100000.0) {|x| f2(x)}
puts "Trapezoid 2: ", z, "\n" # 1.170995544

z = series_sum(0.5, 100) {|x, i| series_f(x, i)}
puts "Series sum: ", z, "\n"
