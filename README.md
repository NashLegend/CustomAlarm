CustomAlarm
===========
A custom alarm

In **API19+** AlarmManager.set(),setRepeating() are no longer exact.

Inexact alarm may have a **5 minute deviation**

To use exact alarm,use **sexExact()** method instead.

In **API19+, repeating alarm cannot be exact**, if u need exact repeating alarm, please repeat setExact() method……
