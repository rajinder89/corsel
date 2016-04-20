
;check for arguments
if $CmdLine[0] < 1 Then
MsgBox(0, "Error", "No command line arguments were specified." & @LF & "Specify the window name you want to maximize in the command line to run test.")
Exit
EndIf

AutoItSetOption ( "WinTitleMatchMode",2)
If WinExists ( $CmdLine[1]) Then
		;MsgBox(0, "Error", $CmdLine[1] & " Window Exists")
		WinActivate($CmdLine[1])
		WinSetState($CmdLine[1],"",@SW_MAXIMIZE)
EndIf






