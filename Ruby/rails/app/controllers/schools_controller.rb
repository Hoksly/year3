class SchoolsController < ApplicationController
  def index
    @schools = School.all
    @students = Student.all

    @prize_schools = Student.where("place <= 3")
    @most_successful_school = Student.where("place <= 3").
      group(:school).limit(5)
    @winners = Student.where("place = 1")
  end

  def show
    @school = School.find(params[:id])
  end

  def new
    @school = School.new
  end

  def create
    @school = School.new(school_params)

    if @school.save
      redirect_to @school
    else
      render :new, status: :unprocessable_entity
    end
  end

  def edit
    @school = School.find(params[:id])
  end

  def update
    @school = School.find(params[:id])

    if @school.update(school_params)
      redirect_to @school
    else
      render :edit, status: :unprocessable_entity
    end
  end

  def destroy
    @school = School.find(params[:id])
    @school.destroy

    redirect_to root_path, status: :see_other
  end

  private
  def school_params
    params.require(:school).permit(:number, :address)
  end
end
