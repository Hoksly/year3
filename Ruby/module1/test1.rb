require 'minitest/autorun'
require_relative 'task1'  # Replace with the actual file name containing the Function and Integrator classes

class FunctionTest < Minitest::Test
  def test_initialize_sets_instance_variables_correctly
    function = Function.new(1, 2, 3)
    assert_equal 1, function.instance_variable_get(:@a)
    assert_equal 2, function.instance_variable_get(:@b)
    assert_equal 3, function.instance_variable_get(:@c)
  end

  def test_initialize_sets_output_mode_correctly
    function = Function.new(1, 2, 3)
    assert_equal 0, function.instance_variable_get(:@output_mode)
  end

  def test_call_returns_correct_result_for_specific_conditions
    function = Function.new(1, 2, 3)

    result1 = function.call(3)
    assert_equal -11, result1

    result2 = function.call(7)
    assert_equal -3, result2
  end
end

class IntegratorTest < Minitest::Test
  def test_integrate_integrates_function_correctly
    function = Function.new(1, 1, 1)

    result = Integrator.integrate(function, 0, 10, 1)

    assert_equal [-1.0, -2.0, -5.0, -10.0, -17.0, -5.0, -6.0, -7.0, -8.0, -9.0], result
  end
end
