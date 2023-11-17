def task1
  x = 2.0
  sum = 1
  (2..11).each do |n|
    sum -= n.to_f / (n+1).to_f * (x**(n-1)).to_f
  end
  puts "Sum: #{sum}"
end


def task5(n)
  sum = 0
  for i in 1..n
    sum += 2
    sum = Math.sqrt(sum)
  end
  puts "Sum: #{sum}"

end

task5(3)
