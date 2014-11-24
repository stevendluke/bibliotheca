'''
Created on Nov 9, 2014

@author: sluke
'''

from google.appengine.ext import ndb

class User(ndb.Model):
    '''
    User Object
    '''

    email = ndb.StringProperty()
    password = ndb.StringProperty()
    name = ndb.StringProperty()

class Book(ndb.Model):
    '''
    Book Object
    '''

    isbn = ndb.StringProperty()
    title = ndb.StringProperty()
    author = ndb.StringProperty()
    genre = ndb.StringProperty()
    description = ndb.StringProperty()
    publication = ndb.BlobProperty()

    # There will be a download object that will use a Book as its key.
    # The book will have a key that is the User to have an entity group

