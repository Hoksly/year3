#Variant 8

def func(c)
  d = []
  duplications = {}
  c.size.times { |i|
    if !duplications.key?(c[i])
      duplications[c[i]] = 1
    else
      duplications[c[i]] += 1
    end
  }
  indexes_dict = {}
  c.size.times  { |i|
    if duplications[c[i]] == 1
      d.push(c[i])
    else
      if indexes_dict.key?(c[i])
        d.push(indexes_dict[c[i]])
        indexes_dict[c[i]] += 1
      else
        d.push(1)
        indexes_dict[c[i]] = 2
      end
    end
  }
  d
end

c1 = [1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 3, 4, 5, 5, 5]
c2 = [3, 3, 6, 3, 4, 1, 2, 8, 8, 1, 1, 2, 7, 7, 7, 7]

print func(c1), "\n"
print func(c2), "\n"
