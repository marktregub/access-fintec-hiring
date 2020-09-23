# Access Fintech hiring test

### Implementation notes

 - The command line tool execute command: 
 java accessfintec.hiring.StockProcessorMain [resources list]
  [resources list]  - list of files separated by " " in the full path
 - In order to query the server write to socket on port 2424. In linux the command looks like this:
 nc 127.0.0.1 2424 < /tmp/1.txt
 where the content of the input file (1.txt) is 1 or more commands like this:
    - [ ] GetAllLowestPrices
    - [ ] GetLowestPrice [stockName]