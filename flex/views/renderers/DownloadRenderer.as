package views.renderers {
import flash.events.MouseEvent;
import flash.net.URLRequest;
import flash.net.navigateToURL;

import mx.controls.advancedDataGridClasses.AdvancedDataGridGroupItemRenderer;


public class DownloadRenderer extends AdvancedDataGridGroupItemRenderer {

    public function DownloadRenderer() {
        super();
        this.addEventListener(MouseEvent.CLICK, clickLink);
    }

    private function clickLink(event:MouseEvent):void {
        if (data.hasOwnProperty("@viewURL")) {
            var url:URLRequest = new URLRequest(data.@viewURL);
            navigateToURL(url, '_blank');
        }
    }

    override protected function updateDisplayList(w:Number, h:Number):void {
        super.updateDisplayList(w, h);
        if (data != null) {

            if (data.hasOwnProperty("@viewURL") && data.@viewURL != "") {
                label.text = data.@displayName;
                this.setStyle("color", "Blue");

                if (data.hasOwnProperty("@PROTECTED") && data.@PROTECTED == "Y") {
                    this.setStyle("color", "Purple");
                }

            }
            else {
                label.text = getDownloadName(data);

                this.setStyle("color", "Black");
                if (data.hasOwnProperty("@PROTECTED") && data.@PROTECTED == "Y") {
                    this.setStyle("color", "Red");
                }
            }
            super.label.setActualSize(Math.max(unscaledWidth - super.label.x, 4), unscaledHeight);
        }
    }

    override public function set data(value:Object):void {
        super.data = value;

    }

    private function getDownloadName(item:Object):String {
        var empty:String = item.name() == "Request" && item.hasOwnProperty("@isEmpty") && item.@isEmpty == "Y" ? " (no downloads)" : "";
        if (item.name() == "RequestDownload") {
            if (item.hasOwnProperty("@itemNumber") && item.@itemNumber != '') {
                var results:String = item.hasOwnProperty("@results") && item.@results != '' ? " - " + item.@results : '';
                return item.@itemNumber + results + empty;
            } else {
                return item.@results + empty;
            }
        } else {
            return item.@displayName + empty;
        }
    }
}
}
