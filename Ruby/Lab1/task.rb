def calc_function(x, b, g)
  result1 = Math.tan((Math::PI / 4) + x / 2)
  result2 = Math.log(Math.exp(g) + 4.1 * 10**2.1) 
  result3 = Math.sin(x / 2)
  result4 = Math.sqrt(b - 1) ** 3

  if result2 == 0 || result4 == 0
    raise ZeroDivisionError, "divided by 0"
  endgit 

  if result3 / result4 < -1 || result3 / result4 > 1
    raise Math::DomainError, "Math::acos argument out of range [-1, 1]"
  end
  
  final_result = Math.acos(result3 / result4) + result2 / result1


  return final_result
end

puts "Enter x: "
x = gets.chomp.to_f
puts "Enter b: "
b = gets.chomp.to_f
puts "Enter g: "
g = gets.chomp.to_f

begin
  puts "Result: #{calc_function(x, b, g)}"
rescue Math::DomainError => e
  puts "Error: #{e.message}"
rescue ZeroDivisionError => e
  puts "Error: #{e.message}"
end
