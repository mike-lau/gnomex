package hci.flex.renderers
{
	import mx.core.ClassFactory;
	import mx.core.IFactory;
	
	public class RendererFactory {
		public static var DEFAULT_ERROR_BACKGROUND:uint = 0xFFCCCC;
		public static var DEFAULT_MISSING_REQUIRED_FIELD_BACKGROUND:uint = 0xFFFFB9;
		public static var DEFAULT_MISSING_REQUIRED_FIELD_BORDER:uint = 0xffff00;
		public static var DEFAULT_MISSING_REQUIRED_FIELD_BORDER_THICKNESS:uint = 0;
		public static var DEFAULT_HIGHLIGHT_BACKGROUND:uint = 0xFFFF99;
		public static var DEFAULT_MISSING_FIELD_BACKGROUND:uint = 0xeaeaea;
		public static var DEFAULT_HIGHLIGHT_COLOR:uint = 0xCCFFCC;
		public static var DEFAULT_TEXT_COLOR:uint =0x000000;
		

		public static function create(renderer:Class, properties:Object):IFactory {  
			var factory:ClassFactory = new ClassFactory(renderer);   
			factory.properties = properties;  
			return factory;
		}            


	}
}