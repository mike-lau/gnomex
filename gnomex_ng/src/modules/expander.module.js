"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var common_1 = require("@angular/common");
//import { jqxExpanderComponent } from 'jqwidgets-framework';
var angular_jqxexpander_1 = require("../assets/jqwidgets-ts/angular_jqxexpander");
var ExpanderModule = (function () {
    function ExpanderModule() {
    }
    return ExpanderModule;
}());
ExpanderModule = __decorate([
    core_1.NgModule({
        imports: [common_1.CommonModule],
        declarations: [angular_jqxexpander_1.jqxExpanderComponent],
        exports: [angular_jqxexpander_1.jqxExpanderComponent],
    })
], ExpanderModule);
exports.ExpanderModule = ExpanderModule;
