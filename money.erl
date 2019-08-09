-module(money).
-export([start/0, master_process/2, random/2]).

start() ->
	{ok, CustomersData} = file:consult("customers.txt"),
	{ok, BanksData} = file:consult("banks.txt"),
	master_process(CustomersData, BanksData).


master_process(CustomersData, BanksData) ->
	Customers = get_key(CustomersData, []),
	io:format("** Customers and loan objectives **\n", []),
	show(Customers, CustomersData),
	
	Banks = get_key(BanksData, []),
	io:format("** Banks and financial resources **\n", []),
	show(Banks, BanksData),
	createpid(Customers, customer, CustomersData, Banks),
	createpid(Banks, bank, BanksData, Banks),
	master().

  
master() ->
	receive
		{NamePid, Money, unavailable_bank} -> 
			io:format("~p was only able to borrow ~p dollar(s). Boo Hoo!\n", [NamePid, Money]), 
			master();
		{NamePid, Money, enough} ->
			io:format("~p has reached the objective of ~p dollar(s). Woo Hoo!\n", [NamePid, Money]), 
			master();
		{NamePid, close, Remaining} ->
			io:format("~p has ~p dollar(s) remaining.\n", [NamePid, Remaining]), 
			master();
		
		{Customer, Money, request, Bank} -> 
			io:format("~p requests a loan of ~p dollar(s) from ~p\n", [Customer, Money, Bank]), 
			master();
		
		{Bank, Money, reject, Customer} -> 
			io:format("~p denies a loan of ~p dollars from ~p\n", [Bank, Money, Customer]), 
			master();
		{Bank, Money, approve, Customer} -> 
			io:format("~p approves a loan of ~p dollars from ~p\n", [Bank, Money, Customer]), 
			master()
	after 
		5000 -> ok
	end.


%% create list process 
createpid([A | Tail], Module, Data, Banks) ->
	Pid = case Module of
	 customer -> spawn(Module, init, [self(), proplists:get_value(A, Data, []), Banks]);
	 _ -> spawn(Module, init, [self(), proplists:get_value(A, Data, [])])
	 end,
	register(A, Pid),
	createpid(Tail, Module, Data, Banks);
createpid([], _Module, _Data, _Banks) -> ok.

%% show content of file
show([], _) -> io:format("\n", []);
show([Head| Tail], Result) ->
	io:format("~p: ~p\n", [Head, proplists:get_value(Head, Result, [])]),
	show(Tail, Result).
 
get_key([], Result) -> Result;
get_key([{Key, _}| Tail], R) ->
get_key(Tail, R ++ [Key]).

random(From, To) ->
	crypto:rand_uniform(From, To).


