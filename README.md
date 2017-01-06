# theBigNerdRanch

This repository will hold a series of apps, each demonstrating a feature(s) of android.

<ol>
<li>
	<b>GeoQuiz</b> - a basic app that exists to demo Toast.makeText(...) via a Button press.
</li>
<li>
	<b>GeoQuiz v1.1</b> - This version shows onClickListener in 2 forms, looping through a list of 
	ResId's with mod to display text, it also shows how to organize widgets using sub-LinearLayouts,
	and how to add images to buttons.
</li>
<li>
	<b>GeoQuiz v1.2</b> - This app shows the full life cycle of the app by using Log.d(...) and Log.i(...). 
	It also demonstrates how the custom horizontal layout can look different from protrait layout
	and how its opened when the device is rotated.Finally the app also shows how to save primitive types.
</li>
<li>
	<b>GeoQuiz v2.0</b> - This app displays how to call another activity which must call back when it
	leaves the stack (ie. is finished). Intent construction encapsulation is also on display inside the 
	cheat Activity. The Cheat activity builds intents for those that wish to call it and it unpacks
	intents that it has created for those that need it. Intents also include exta data the next activity
	might need. The cheatActivity should now display differntly the results if SDK 21+ is used.
</li>
<li>
	<b>Criminal Intent (work on-going...)</b>
	<br/>- update v1.7 - Added a new TimePicker button and adjusted the xml weight property.
	<br/>- update v1.6 - The user can now alter the date of the crime and the result is passed back to the crime and the list is refreshed. This update also shows Fragments communicating which is different when an Activity is involved. 
	<br/>- update v1.5 - Added ActivityPager so now the ui can be swiped from left to right without going back to the list.
	<br/>- update v1.4 - Refined the use of viewHolder and RecyclerView. I've added the adapter so one item 
	in the list refreshes when a change to a crime has occured. Bundles and args are also used explicitly over 
	savedInstanceState to improve maintainability in 5 years where changes could lead to mistakes.
	<br/>- update v1.3 - This app is now in a pre-alpha testable state to demonstate the functionality of ViewHolder, 
	RecyclerView, and fragments. The comments have also been updated to provide more clarity on how it all works. The next
	update will improve the crime class and remove the loop to create crimes and allow the user to create only what they need.
	<br/>- update v1.2 - This app expands on v1.1 by expanding into to the use of viewHolder, RecyclerView,
	a generic reusable fragment.
	<br/>- update v1.1 - This app expands on v1.0 by wiring up the widgets in the fragment and adding
	logs to see changes to textbox and checkbox as it happens. Activity is arranged differently in land
	scape mode and the time is formatted in a more friendly way.
	<br/>- update v1.0 - A basic app that exists to demo fragments using the support library over the built
	in fragment class. Currently, this app displays a fragment with a simple edit text widget.
</li>
</ol>
<br/>
<br/>
<br/>
A few important defintions:
