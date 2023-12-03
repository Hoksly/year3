class CreateSchools < ActiveRecord::Migration[7.0]
  def change
    create_table :schools do |t|
      t.string :number
      t.string :address

      t.timestamps
    end
  end
end
