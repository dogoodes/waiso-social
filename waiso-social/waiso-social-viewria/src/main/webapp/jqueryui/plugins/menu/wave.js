// JavaScript Document
	
$(document).ready(function(){
	// Menu Header
	$('ul#nav li>a').hide();
	var last = null;
	$('ul#nav>li').mouseenter(function(){
		last = this;
		$(this).find('a:first-child').not(':animated').fadeIn(150);
	}).mouseleave(function(){
		$(this).find('a:first-child').fadeOut(150);
	});
	
	// Menu Auxiliar
	$('ul#aux li>a').hide();
	var last = null;
	$('ul#aux>li').mouseenter(function(){
		last = this;
		$(this).find('a:first-child').not(':animated').fadeIn(150);
	}).mouseleave(function(){
		$(this).find('a:first-child').fadeOut(150);
	});
	
	// Menu Serviços
	$('ul#nav li.bt_menu a').click(function(){
		$('div.menu-servicos').fadeIn(250);
		//$('div.menu-servicos').slideDown('slow');
	});
	$('ul#aux li.bt_voltar a').click(function(){
		$('div.menu-servicos').fadeOut(250);
		//$('div.menu-servicos').slideUp('slow');
	});
});

$(function() {
	// Menu Footer
	
	function highlight(items){items.filter(":eq(0)").addClass('ativo')}
	function unhighlight(items){items.removeClass('ativo')}
	
	$('ul#menu').carouFredSel({
		width: 702,
		height: 'auto',
		prev:	{
					button:'#prev',
					onBefore: function(oldItems, newItems) {
						unhighlight( oldItems );
					},
					onAfter	: function(oldItems, newItems) {
						highlight( newItems );
					}
				},
		next:	{
					button:'#next',
					onBefore: function(oldItems, newItems) {
						unhighlight( oldItems );
					},
					onAfter	: function(oldItems, newItems) {
						highlight( newItems );
					}
				},
		scroll: 1,
		auto: false
	});
	
	unhighlight( $("ul#menu > *") );
	highlight( $("ul#menu > *") );
});