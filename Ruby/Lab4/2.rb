#Variant 8

def norm(x)
  s = x.inject(0){ |res, elem| res + (elem ** 2)}
  s ** (0.5)
end

x = [3, -4, 1]
print norm(x), "\n"


