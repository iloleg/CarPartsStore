$(document).ready(
    function() {
        function get_users() {
            $.post('get_users', function(responce) {
                console.log(responce);
                var data = JSON.parse(responce);
                $(".table-responsive").html('').append(
                    CPS.$Table(data, ["Username", "Role"], [
                        "username", "role"
                    ], ["object-id"], ["id"]));
            });
        }

        $("#btn-delete").click(function() {
            var id = $("#chosen-record").attr("object-id");
            console.log(id);
            if (confirm("Are you sure?")) {
                $.post('delete_user', {
                    "id": id
                }, function(responce) {
                    console.log(responce);
                    if (JSON.parse(responce).status == "success") {
                        alert("Successfully deleted!");
                        get_users();
                    } else {
                        alert("Cannot delete this record.");
                    };
                });
            }
        });

        get_users();
    });