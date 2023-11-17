# Given values
a = false
b = false
c = true
x = 1
y = 16
z = -40

# a) ¬(A ∨ B) ∧ (A ∧ ¬B)
result_a = !(a || b) && (a && !b)
puts "Result a: #{result_a}"

# b) (Z ≠ Y) ∧ (6 ≥ Y) ∧ (A ∨ B) ∧ C ∧ (X ≥ 1.5)
result_b = (z != y) && (6 >= y) && (a || b) && c && (x >= 1.5)
puts "Result b: #{result_b}"

# c) (8 – X * 2 ≤ Z) ∧ (X^2 >= Y^2) ∨ (Z ≥ 15)
result_c = (8 - x * 2 <= z) && (x**2 >= y**2) || (z >= 15)
puts "Result c: #{result_c}"

# d) X > 0 ∧ Y < 0 ∨ Z ≥ (X*Y + (-Y/X)) + (-Z)*(-2)
result_d = x > 0 && y < 0 || z >= (x*y + (-y/x)) + (-z)*(-2)
puts "Result d: #{result_d}"

# g) ¬(A ∨ B ∧ ¬(C ∨ (¬A ∨ B)))
result_g = !(a || b && !(c || (!a || b)))
puts "Result g: #{result_g}"

# h) X^2 + Y^2 ≥ 1 ∧ X ≥ 0 ∧ Y ≥ 0
result_h = x**2 + y**2 >= 1 && x >= 0 && y >= 0
puts "Result h: #{result_h}"

# i) (A ∧ (C ∧ B <> B ∨ A) ∨ C) ∧ B
result_i = (a && (c && b != b || a) || c) && b
puts "Result i: #{result_i}"