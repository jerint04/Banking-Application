-module(bank).
-export([init/2, process/4]).

init(Master, TotalMoney) ->
	{_, NamePid} = erlang:process_info(self(), registered_name),
    timer:sleep(100),
	process(Master, TotalMoney, TotalMoney ,NamePid).

process(Master, TotalMoney, Remaining ,NamePid) ->
	receive
		{CustomerPid, borrow, Money} -> 
			NewTotal = resp_request(Master, NamePid, CustomerPid, Money, TotalMoney),
			process(Master, NewTotal, Remaining ,NamePid)
	after 
		4000 -> Master ! {NamePid, close, TotalMoney}
	end.

resp_request(Master, NamePid, CustomerPid, Money, TotalMoney) when TotalMoney >= Money ->
	CustomerPid ! {NamePid, approve, Money},
	Master ! {NamePid, Money, approve, CustomerPid},
	TotalMoney - Money;
resp_request(Master, NamePid, CustomerPid, Money, TotalMoney)  ->
	CustomerPid ! {NamePid, reject, Money},
	Master ! {NamePid, Money, reject, CustomerPid},
	TotalMoney.