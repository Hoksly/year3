# phone_test.rb

require 'minitest/autorun'  # Assuming your Phone class is in a file named 'phone.rb'
require_relative 'task2'  # Assuming your PhoneArray class is in a file named 'phone_array.rb'

class PhoneTest < Minitest::Test
  def setup
    @phone1 = Phone.new(1, 'Smith', 'John', 'Doe', '123 Main St', '1234-5678-9012-3456', 100, 50, 30, 10)
    @phone2 = Phone.new(2, 'Johnson', 'Alice', 'Marie', '456 Oak St', '9876-5432-1098-7654', 150, 20, 20, 5)
    @phone3 = Phone.new(3, 'Doe', 'Bob', 'Robert', '789 Elm St', '5678-1234-5678-9012', 200, 100, 40, 15)
  end

  def test_set_tun
    @phone1.set_tun(40, 15)
    assert_equal [40, 15], @phone1.get_tun
  end

  def test_to_string
    expected_output = "ID: 1, Name: Smith John Doe, Address: 123 Main St, Credit Card: 1234-5678-9012-3456, Debit: 100, Credit: 50, Local Calls: 30, Long Distance Calls: 10"
    assert_equal expected_output, @phone1.to_s
  end
end

class PhoneArrayTest < Minitest::Test
  def setup
    @phone1 = Phone.new(1, 'Smith', 'John', 'Doe', '123 Main St', '1234-5678-9012-3456', 100, 50, 30, 10)
    @phone2 = Phone.new(2, 'Johnson', 'Alice', 'Marie', '456 Oak St', '9876-5432-1098-7654', 150, 20, 20, 5)
    @phone3 = Phone.new(3, 'Doe', 'Bob', 'Robert', '789 Elm St', '5678-1234-5678-9012', 200, 100, 40, 15)

    @phone_array = PhoneArray.new
    @phone_array.add_phone(@phone1)
    @phone_array.add_phone(@phone2)
    @phone_array.add_phone(@phone3)
  end

  def test_local_calls_exceed
    result = @phone_array.local_calls_exceed(25)
    assert_equal [@phone1, @phone3], result
  end

  def test_used_long_distance_calls
    result = @phone_array.used_long_distance_calls
    assert_equal [@phone1, @phone2, @phone3], result
  end

  def test_sort_alphabetically
    result = @phone_array.sort_alphabetically
    assert_equal [@phone3, @phone2, @phone1], result
  end
end
