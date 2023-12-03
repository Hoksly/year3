class CreateStudents < ActiveRecord::Migration[7.0]
  def change
    create_table :students do |t|
      t.string :surname
      t.references :school, null: false, foreign_key: true
      t.integer :grade
      t.integer :place

      t.timestamps
    end
  end
end
