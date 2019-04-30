<form method="post" action="signup" >
	<input type="hidden" name="method"  value="POST">
	<input type="hidden" name="id" value="0">
	<div class="form-group">
		<label for="username">Username</label>
		<input type="text" name="username" id=username class="form-control">
	</div>
	<div class="form-group">
		<label for="password">Password</label>
		<input type="password" id=password name="password" class="form-control">
	</div>
	<div class="form-group">
		<label for="firstName">First name</label>
		<input type="text" name="firstName" id='firstName' class="form-control">
	</div>
	<div class="form-group">
		<label for="lastName">Last name</label>
		<input type="text" name="lastName" id="lastName" class="form-control">
	</div>
	<input type="submit" value="Add User" class="btn btn-primary">
</form>
