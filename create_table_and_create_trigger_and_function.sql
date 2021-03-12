create table accounts.accounts (
	
	-- serial auto increments
	account_id serial primary key,
	-- account_id int primary key,
	
	-- username and password
	user_name text not null,
	pass_word text not null,

	-- full name
	first_name text not null,
	middle_name text not null,
	last_name text not null,
	
	-- full address
	street text not null,
	city text not null,
	state text not null,
	zip_code text not null,
	
	-- contact information
	email text not null,
	phone_number text not null,

	-- account balances
	checking_account_balance INT not null,
	savings_account_balance INT not null

);

truncate accounts.accounts;

select * from accounts.accounts;

create or replace function update_account_with_signup_bonus()
	returns trigger as $$
	begin
		update accounts.accounts set checking_account_balance = 100, savings_account_balance = 100 
			where account_id = (select max(account_id) from accounts.accounts);
		return new;
	end;
$$ language plpgsql;

create trigger accounts_signup_bonus
	after insert on accounts.accounts
	for each row
		execute procedure update_account_with_signup_bonus();

drop trigger accounts_signup_bonus on accounts.accounts;