def gauss
  n = gets.chomp.to_i
  if n < 3 || n > 9
    raise "N is out of bounds"
  end

  b = Array.new(n){|index| index + 1}
  a = (0..n - 1).map{Array.new(n - 1)}
  a.size.times do |i|
    a.size.times do |j|
      if i == j
        a[i][j] = 2.0
      else
        a[i][j] = 8.0
      end
    end
  end

  puts a.map(&:inspect), "\n"

  a.size.times do |i|
    coef = a[i][i]
    a.size.times do |j|
      a[i][j] /= coef
    end
    b[i] /= coef
    a.size.times do |j|
      if j != i
        coef1 = a[j][i] / a[i][i]
        a.size.times do |k|
          a[j][k] -= a[i][k] * coef1
        end
        b[j] -= b[i] * coef1
      end
    end
    puts a.map(&:inspect), "\n"
  end

  b
end

print gauss, "\n"
