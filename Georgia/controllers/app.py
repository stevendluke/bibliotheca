'''
Created on Nov 9, 2014

@author: sluke
'''

from django.http import HttpResponseRedirect
from django.views.generic.simple import direct_to_template

from data.models import Book

def main_page(request):
    book = Book.query(Book.isbn == "1234").fetch()

    template_values = {
        "book": book[0]
    }

    return direct_to_template(request, "main_page.html", template_values)

def save_book(request):
    book = Book()

    book.isbn = "1234"
    book.title = "All About Steven"
    book.author = "Steven Luke"

    book.put()

    return HttpResponseRedirect("/")