class Function
    def initialize(a, b, c)
      @a = a
      @b = b
      @c = c

      @output_mode = ((a.to_i | b.to_i) + (a.to_i & c.to_i) % 2) == 0 ? 1 : 0
    end

    def call(x)
        result = 0
        
      
        if(x < 5 && @c != 0)
          result = - (@a * x * x + @b)
       
        elsif(x > 5 && @c == 0)
          if(x == 0)
            raise "Division by zero"
          end

          result = (x - @a) / x 
        

        else
          if(@c == 0)
            raise "Division by zero"
          end
          result = - x / @c
        end
        
        if @output_mode == 1
          result = result.to_i
        end
        
        return result
        
    end
end


class Integrator
  def self.integrate(f, x_beg, x_end, dx)
    result = Array.new(((x_end - x_beg) / dx).to_i, 0)
     
    x = x_beg

    while x < x_end

      result[(x - x_beg) / dx] = f.call(x)
      x += dx
    end

    result
  end
end

