var app = {}, router;

app.settings = {
    baseUrl: '/api/',
    content: $('#content')
};

$.ajaxSetup({
    dataType: 'json'
});

app.templates = {
    list: _.template($('#template-list').html()),
    form: _.template($('#template-form').html()),
};

app.repo = {
    getAll: function() {
        $.ajax({
            type: "GET",
            url: app.settings.baseUrl + 'bookmarks',
            success: function(bookmarks) {
                app.settings.content.html(app.templates.list({ bookmarks: bookmarks }));
            },
            error: function() {
                noty({
                    text: 'Unable to fetch list',
                    type: 'error'
                });
            }
        });
    },
    find: function(id, callback) {
        $.ajax({
            type: "GET",
            url: app.settings.baseUrl + 'bookmarks/' + id,
            success: function(bookmark) {
                callback.call(null, bookmark);
            },
            error: function() {
                noty({
                    text: 'Unable to save',
                    type: 'error'
                });
            }
        });
    },
    save: function(bookmark) {
        $.ajax({
            type: "POST",
            url: app.settings.baseUrl + 'bookmarks',
            data: bookmark,
            success: function() {
                noty({
                    text: 'Saved',
                    type: 'success'
                });

                router.navigate("bookmarks", { trigger: true });
            },
            error: function() {
                noty({
                    text: 'Unable to save',
                    type: 'error'
                });
            }
        });
    },
    update: function(bookmark, id) {
        $.ajax({
            type: "PUT",
            url: app.settings.baseUrl + 'bookmarks/' + id,
            data: bookmark,
            success: function() {
                noty({
                    text: 'Updated',
                    type: 'success'
                });

                router.navigate("bookmarks", { trigger: true });
            },
            error: function() {
                noty({
                    text: 'Unable to update',
                    type: 'error'
                });
            }
        });
    },
    destroy: function(id) {
        $.ajax({
            type: "DELETE",
            url: app.settings.baseUrl + 'bookmarks/' + id,
            success: function() {
                noty({
                    text: 'Deleted',
                    type: 'success'
                });

                router.index();
            },
            error: function() {
                noty({
                    text: 'Unable to delete',
                    type: 'error'
                });
            }
        });
    }
};

window.Router = Backbone.Router.extend({
    routes: {
        "": "home",
        "bookmarks": "index",
        "bookmarks/create": "create",
        "bookmarks/:id/edit": "edit"
    },
    initialize: function() {
    	app.settings.content.on("click", '[data-type="cancel"]', function(e) {
    		e.preventDefault();
    		router.navigate("bookmarks", { trigger: true });
    	});
    	
    	app.settings.content.on("click", '[data-type="delete"]', function(e) {
    		e.preventDefault();
    		
    		var id = $(this).data('id');
    		if(id) {
    			if(confirm("Are you sure ?")) {
    				app.repo.destroy(id);
    			}
    		}
    	});
    },
    home: function() {
        console.log('Calling: home');
        this.navigate("bookmarks", { trigger: true });
    },
    index: function() {
        console.log('Calling: index');
        app.repo.getAll();
    },
    create: function() {
        console.log('Calling: create');
        app.settings.content.html(app.templates.form({ url: '', title: '', mode: 'add' }));
        $("#formBookmark").validate({
            submitHandler: function(form) {
                app.repo.save($(form).serialize());
            }
        });
    },
    edit: function(id) {
        console.log('Calling: edit');
        app.repo.find(id, function(bookmark) {
            bookmark.mode = 'update';
            app.settings.content.html(app.templates.form(bookmark));
            $("#formBookmark").validate({
                submitHandler: function(form) {
                    app.repo.update($(form).serialize(), id);
                }
            });
        });
    }
});

_.templateSettings = {
    interpolate: /\{\{(.+?)\}\}/g
};
$.noty.defaults.timeout = 2000;
$.noty.defaults.layout = 'topCenter';

router = new Router();
Backbone.history.start();