package com.izerui.components.tree
{
	import flash.events.Event;
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	import mx.controls.Tree;
	import mx.events.ListEvent;
	
	[Bindable]
	[Event(name="Item_Double_Click", type="flash.events.Event")]
	
	[Bindable]
	[Event(name="Item_Click", type="flash.events.Event")]
	
	public class DoubleClickTree extends Tree
	{
		
		private var isDoubleClick:Boolean = false;//判断是否是双击的标志
		
		
		public function DoubleClickTree()
		{
			//TODO: implement function
			doubleClickEnabled = true;
			addEventListener(ListEvent.ITEM_DOUBLE_CLICK,doubleClickFun);
			addEventListener(ListEvent.ITEM_CLICK,itemClickFun);
			
			super();
		}
		
		//单机
		protected function itemClickFun(event:ListEvent):void
		{
			// TODO Auto-generated method stub
			isDoubleClick = false;
			var timer:Timer = new Timer(260, 1);
			timer.start();//两次单击间隔在260毫秒之内的就被认为是双击
			timer.addEventListener(TimerEvent.TIMER,clickTimerCheck);
			
		}
		
		//判断单机还是双机
		protected function clickTimerCheck(event:TimerEvent):void
		{
			// TODO Auto-generated method stub
			if(isDoubleClick){ 
				//展开节点或者关闭节点
				if(dataDescriptor.isBranch(selectedItem)){
					expandItem(selectedItem, !isItemOpen(selectedItem), true); 
				}
				dispatchEvent(new Event("Item_Double_Click"));
				
			}else{
				dispatchEvent(new Event("Item_Click"));
			}
			
		}
		//双机
		protected function doubleClickFun(event:ListEvent):void
		{
			// TODO Auto-generated method stub
			isDoubleClick = true;
			
		}
	}
}