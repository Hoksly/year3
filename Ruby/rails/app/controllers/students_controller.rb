class StudentsController < ApplicationController
  def create
    @school = School.find(params[:school_id])
    @student = @school.students.create(student_params)
    redirect_to school_path(@school)
  end

  def edit
    @school = School.find(params[:school_id])
    @student = Student.find(params[:id])
  end

  def update
    @student = Student.find(params[:id])

    if @student.update(student_params)
      redirect_to root_path
    else
      render :edit, status: :unprocessable_entity
    end
  end

  def destroy
    @student = Student.find(params[:id])
    @student.destroy
    redirect_to root_path
  end

  private
  def student_params
    params.require(:student).permit(:surname, :grade, :place)
  end
end
