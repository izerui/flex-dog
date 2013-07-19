package com.izerui.utils
{
	
	import mx.collections.ArrayCollection;

	public class ArrayCollectionUtils
	{
		public function ArrayCollectionUtils()
		{
		}
		
		/**
		 * 从一个集合中移除一个对象
		 * @param coll 集合
		 * @param obj 要比较的对象.如果没有设置property 默认就是当前集合的对象
		 * @param property 集合中对象的某个属性. 这个时候 obj就要提供该属性的值作为参照
		 */
		public static function removeObjFromArrayCollection(coll:ArrayCollection,obj:Object,property:String=null):void{
			var len:uint = coll.length;
			for(var i:Number = len-1; i > -1; i--)
			{
				if(property){
					if(coll.getItemAt(i)[property] == obj){
						coll.removeItemAt(i);
						break;
					}
				}else{
					if(coll.getItemAt(i) == obj)
					{
						coll.removeItemAt(i);
						break;
					}
				}
			}	
		}
		
		
		/**
		 * 取前SIZE条数据并返回
		 * @param coll 集合
		 * @param SIZE 长度
		 */
		public static function getPreSizeCollection(coll:ArrayCollection,size:int):ArrayCollection{
			var _sizeCollection:ArrayCollection = null;
			if(coll){
				_sizeCollection = new ArrayCollection;
				for(var i:Number = 0; i<(coll.length<size?coll.length:size); i++)
				{
					_sizeCollection.addItem(coll.getItemAt(i));
				}	
			}
			return _sizeCollection;
		}
		
		public static function getIndexForItem(coll:ArrayCollection,obj:Object):int{
			for(var i:int=0;i<coll.length;i++)
			{
				if(coll.getItemAt(i) == obj)
				{
					return i;
				}
			}
			return -1;
		}
		/**
		 * 检查集合中字段.是否满足不重复,不为空条件.如果都满足返回true 否则返回false
		 */
		public static function validateArrayCollectionProperty(coll:ArrayCollection,property:String):Boolean{
			for each(var obj:Object in coll){
				if(!obj[property]){
					return false;
				}
				if(validateArrayCollectionHasValue(coll,property,obj[property])>1){
					return false;
				}
			}
			return true;
		}
		/**
		 * 验证集合对象的property属性包含指定值的次数
		 */
		public static function validateArrayCollectionHasValue(coll:ArrayCollection,property:String,value:Object):int{
			var resNum:int = 0;
			for each(var obj:Object in coll){
				if(obj&&obj[property]==value){
					resNum++;
				}
			}
			return resNum;
		}
		/**
		 * 获取集合中单个对象的某个属性等于value 的这个对象的索引
		 */
		public static function getIndexByCollectionHasValue(coll:ArrayCollection,property:String,value:Object):int{
			var resNum:int = 0;
			for(var i:int=0;i<coll.length;i++){
				var obj:Object = coll.getItemAt(i);
				if(obj&&obj[property]==value){
					return i;
				}
			}
			return -1;
		}
	}
}