class Phone
  attr_accessor :id, :last_name, :first_name, :middle_name, :address, :credit_card_number
  attr_accessor :debit, :credit, :local_calls_time, :long_distance_calls_time

  def initialize(id, last_name, first_name, middle_name, address, credit_card_number, debit, credit, local_calls_time, long_distance_calls_time)
    @id = id
    @last_name = last_name
    @first_name = first_name
    @middle_name = middle_name
    @address = address
    @credit_card_number = credit_card_number
    @debit = debit
    @credit = credit
    @local_calls_time = local_calls_time
    @long_distance_calls_time = long_distance_calls_time
  end

  def set_tun(local_calls_time, long_distance_calls_time)
    @local_calls_time = local_calls_time
    @long_distance_calls_time = long_distance_calls_time
  end

  def get_tun
    [@local_calls_time, @long_distance_calls_time]
  end

  def to_s
    "ID: #{@id}, Name: #{@last_name} #{@first_name} #{@middle_name}, Address: #{@address}, Credit Card: #{@credit_card_number}, Debit: #{@debit}, Credit: #{@credit}, Local Calls: #{@local_calls_time}, Long Distance Calls: #{@long_distance_calls_time}"
  end
end


class PhoneArray
  def initialize
    @phones = []
  end

  def add_phone(phone)
    @phones << phone
  end

  def local_calls_exceed(time_limit)
    @phones.select { |phone| phone.local_calls_time > time_limit }
  end

  def used_long_distance_calls
    @phones.select { |phone| phone.long_distance_calls_time > 0 }
  end

  def sort_alphabetically
    @phones.sort_by { |phone| [phone.last_name, phone.first_name, phone.middle_name] }
  end
end



