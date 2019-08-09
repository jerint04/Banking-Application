-module(customer).
-export([init/3, process/5, randommoney/1]).

init(Master, TotalMoney, ListBanks) ->
	{_, NamePid} = erlang:process_info(self(), registered_name),
	timer:sleep(100),
	make_request(Master, NamePid, ListBanks, randommoney(TotalMoney)),
	process(Master, TotalMoney, ListBanks, 0, NamePid).

process(Master, 0, _ListBanks, Borrowed, NamePid) ->
	Master ! {NamePid, Borrowed, enough};

process(Master, _Money, [], Borrowed, NamePid) ->
	Master ! {NamePid, Borrowed, unavailable_bank};

process(Master, TotalMoney, ListBanks, Borrowed, NamePid) ->
	receive
		{BankPid, reject, Money} ->  
			NewBank = ListBanks -- [BankPid],
			case length(NewBank) of
				0 -> process(Master, TotalMoney, NewBank, Borrowed, NamePid);
				_ -> 
					make_request(Master, NamePid, NewBank, Money),
					process(Master, TotalMoney, NewBank, Borrowed, NamePid)
	
			end;

		{_BankPid, approve, Money} ->	
			NewTotal = TotalMoney - Money,
			if NewTotal > 0 ->	
				make_request(Master, NamePid, ListBanks, randommoney(NewTotal));
			  true -> ok
			end,
			process(Master, NewTotal, ListBanks, Borrowed + Money, NamePid)
	after 
		2000 -> ok
	end.

make_request(Master, NamePid, ListBanks, Money) ->
	timer:sleep(money:random(10, 100)),
	Rand = case length(ListBanks) of
	  1 -> 1;
	  L -> crypto:rand_uniform(1, L)
	  end,
	Bank = lists:nth(Rand, ListBanks),
	Master ! {NamePid, Money, request, Bank},
	Bank ! {NamePid, borrow, Money}.

randommoney(1) -> 1;
randommoney(Total) when Total =< 50 -> money:random(1, Total);
randommoney(_Total) -> money:random(1, 50).

