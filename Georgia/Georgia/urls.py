from django.conf.urls.defaults import patterns
from controllers.app import main_page, sign_in, save_book, about

urlpatterns = patterns('',
    (r'^$', main_page),
    (r'^about$', about),
    (r'^signin$', sign_in),
    (r'^save$', save_book),
)