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

author_service = Service("author", 8)
authorization_service = Service("authorization", 6)
book_club_service = Service("book-club", 14)
book_service = Service("book", 18)
mail_service = Service("mail", 5)
message_service = Service("message", 6)
rate_comment_service = Service("rate-comment", 18)
shelf_service = Service("shelf", 12)


list_of_services = [author_service, authorization_service, book_club_service, book_service, mail_service, message_service, rate_comment_service, shelf_service]

get_random_service_and_function(list_of_services, 24)
