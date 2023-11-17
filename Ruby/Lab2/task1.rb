vertices = [[64,304], [55,281], [53,257], [71,217], [74,181],
            [45,155], [43,128], [53,108], [70,89], [95,77],
            [108,56], [133,50], [190,55], [253,71], [287,54],
            [326,50], [381,56], [413,65], [430,115], [428,158],
            [412,192], [364,222], [318,243], [261,253], [224,268],
            [182,278], [134,290], [105,294]]

vertices << vertices[0]  # Append the first vertex to the end to close the polygon

area = 0
(0...vertices.size-1).each do |i|
  area += vertices[i][0] * vertices[i+1][1] - vertices[i+1][0] * vertices[i][1]
end

area /= 2.0

puts "The area of the polygon is #{area}"
