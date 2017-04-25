        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Manage</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Users
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="table-responsive">
                            </div>
                            <button id="btn-delete" class="btn btn-danger col-lg-offset-10 col-lg-2">Delete Chosen Record</button>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12s -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->
        <script>
        	$(document).ready(function () {
        		function get_users() {
        			$.post('get_users', function (responce) {
            			console.log(responce);
            			var data = JSON.parse(responce);
            		    $(".table-responsive").html('').append(
            		    	CPS.$Table(data, ["Username", "Role"], ["username", "role"], ["object-id"], ["id"])
            		    );
        			});
        		}
        		
        			
            	$("#btn-delete").click(function () {
            		var id = $("#chosen-record").attr("object-id");
            		console.log(id);
           			if (confirm("Are you sure?")) {
           				$.post('delete_user', {"id" : id }, function (responce) {
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
        </script>
