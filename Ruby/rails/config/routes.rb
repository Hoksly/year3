Rails.application.routes.draw do
  root "schools#index"

  resources :schools do
    resources :students
  end
end
