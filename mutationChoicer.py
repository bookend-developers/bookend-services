import random

class Service:
  def __init__(self, name, num_of_functions):
    self.name = name
    self.num_of_functions = num_of_functions
    
def get_random_service_and_function(list_of_services, num_of_services):
    for i in range(1, num_of_services + 1):
        random_service = random.choice(list_of_services)
        list_of_services.remove(random_service)
        print("servis name:", random_service.name,"function_no", random.randint(1, random_service.num_of_functions))

author_service = Service("author", 28)
authorization_service = Service("authorization", 7)
book_club_service = Service("book-club", 39)
book_service = Service("book", 38)
mail_service = Service("mail", 3)
message_service = Service("message", 27)
rate_comment_service = Service("rate-comment", 24)
shelf_service = Service("shelf", 18)


list_of_services = [author_service, authorization_service, book_club_service, book_service, mail_service, message_service, rate_comment_service, shelf_service]

get_random_service_and_function(list_of_services, 2)
