from django.conf.urls.defaults import patterns
from controllers.app import main_page, save_book

urlpatterns = patterns('',
    (r'^$', main_page),
    (r'^save$', save_book),
)