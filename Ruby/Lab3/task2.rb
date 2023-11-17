puts "Enter x"
x = gets.chomp.to_f
y = 0

if x > -4 && x <= 0
  y = (Math.abs(x-2) / (x**2 * Math.cos(x)) )**(1/7.0)

elsif x > 0 && x <= 12
  y = 1 / ((Math.tan(x + 1 / Math.exp(x))  / Math.sin(x)**2) ** (7))
  puts y
   
elsif x < -4 || x > 12
  puts "Enter x"
  y = 1 / (1.0 + x / (1.0 + x / (1.0 + x)))

end

puts "y = #{y}"