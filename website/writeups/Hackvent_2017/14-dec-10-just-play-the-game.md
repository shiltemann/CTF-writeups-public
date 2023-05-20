---
layout: writeup
title: 'Dec 10: Just play the game'
level:
difficulty:
points:
categories: []
tags: []
flag: HV17-y0ue-kn0w-7h4t-g4me-sure

---
*Haven't you ever been bored at school?*

## Challenge

Santa is in trouble. He's elves are busy playing TicTacToe. Beat them
and help Sata to save christmas!

`nc challenges.hackvent.hacking-lab.com 1037`

## Solution

    ---_ ......._-_--.
    (|\ /      / /| \  \
    /  /     .'  -=-'   `.
    /  /    .'             )
    _/  /   .'        _.)   /
    / o   o        _.-' /  .'
    \          _.-'    / .'*|
    \______.-'//    .'.' \*|
    \|  \ | //   .'.' _ |*|
    `   \|//  .'.'_ _ _|*|
    .  .// .'.' | _ _ \*|
    \`-|\_/ /    \ _ _ \*\
    `/'\__/      \ _ _ \*\
    /^|            \ _ _ \*
    '  `             \ _ _ \
                    \_
    Challenge by pyth0n33. Have fun!

    I think you know the game from school...Don't you? ;)

    Press enter to start the game

    -------------
    | * | * | * |
    -------------
    -------------
    | * | * | * |
    -------------
    -------------
    | * | * | * |
    -------------
    Make your move. Type the number of the field you want to set! (1-9)

    Field:

Just a game of tic-tac-toe. We need to win 100 times in a row.

    import telnetlib
    import random

    def isWin(board):
        """
        GIven a board checks if it is in a winning state.
        Arguments:
              board: a list containing X,O or *.
        Return Value:
               True if board in winning state. Else False
        """
        ### check if any of the rows has winning combination
        for i in range(3):
            if len(set(board[i*3:i*3+3])) is  1 and board[i*3] is not '*': return True
        ### check if any of the Columns has winning combination
        for i in range(3):
           if (board[i] is board[i+3]) and (board[i] is  board[i+6]) and board[i] is not '*':
               return True
        ### 2,4,6 and 0,4,8 cases
        if board[0] is board[4] and board[4] is board[8] and board[4] is not '*':
            return  True
        if board[2] is board[4] and board[4] is board[6] and board[4] is not '*':
            return  True
        return False

    def nextMove(board,player):
        """
        Computes the next move for a player given the current board state and also
        computes if the player will win or not.
        Arguments:
            board: list containing X,- and O
            player: one character string 'X' or 'O'
        Return Value:
            willwin: 1 if 'X' is in winning state, 0 if the game is draw and -1 if 'O' is
                        winning
            nextmove: position where the player can play the next move so that the
                             player wins or draws or delays the loss
        """
        ### when board is '*********' evaluating next move takes some time since
        ### the tree has 9! nodes. But it is clear in that state, the result is a draw
        if len(set(board)) == 1: return 0,4

        nextplayer = 'X' if player=='O' else 'O'
        if isWin(board) :
            if player is 'X': return -1,-1
            else: return 1,-1
        res_list=[] # list for appending the result
        c= board.count('*')
        if  c is 0:
            return 0,-1
        _list=[] # list for storing the indexes where '-' appears
        for i in range(len(board)):
            if board[i] == '*':
                _list.append(i)

        for i in _list:
            board[i]=player
            ret,move=nextMove(board,nextplayer)
            res_list.append(ret)
            board[i]='*'
        if player is 'X':
            maxele=max(res_list)
            return maxele,_list[res_list.index(maxele)]
        else :
            minele=min(res_list)
        return minele,_list[res_list.index(minele)]

    def parseboard(s):
        board = s.split('\n')
        newboard = [ board[-9][3],board[-9][7],board[-9][11],
                     board[-6][3],board[-6][7],board[-6][11],
                     board[-3][3],board[-3][7],board[-3][11]]
        return(nextMove(newboard,'X'))

    def play():
        board = tn.read_until("Make your move. Type the number of the field you want to set! (1-9)",0.1)

        if "HV17"  in board:
            print board
            return

        if "Make your move. Type the number of the field you want to set! (1-9)" in board:
            nm = parseboard(board)
            tn.write(str(nm[1]+1)+"\n")
        elif "Congratulations" in board:
            print board

        if "Press enter to start" in board:
            tn.write("\n")


    # Start the game
    server = "challenges.hackvent.hacking-lab.com"
    port = 1037
    tn = telnetlib.Telnet(server, port)

    while True:
        play()
{: .language-python}

we run this code, and after we win game 100 we get the flag:

    -------------
    | O | X | O |
    -------------
    -------------
    | X | X | X |
    -------------
    -------------
    | O | O | * |
    -------------
    Congratulations you won! 100/100

    HV17-y0ue-kn0w-7h4t-g4me-sure
    Press enter to start again

