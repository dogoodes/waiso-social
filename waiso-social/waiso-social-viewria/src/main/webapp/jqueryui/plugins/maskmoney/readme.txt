Just a simple way to create masks to your currency form fields with jquery.
http://github.com/plentz/jquery-maskmoney
USAGE:

<script src="jquery.js" type="text/javascript"></script>
<script src="jquery.maskMoney.js" type="text/javascript"></script>
<script>
$(function(){
  $("#currency").maskMoney();
  $("#real").maskMoney({symbol:"R$", decimal:",", thousands:"."});
  $("#precision").maskMoney({precision:3})
})
function removeMask(){
  $("#currency").unmaskMoney();
}
</script>

Default options are (but you can always change that):
  symbol:'US$',
  decimal:'.',
  precision:2,
  thousands:',',
  allowZero:false,
  showSymbol:false

Copyright (c) 2009 Aurélio Saraiva (aureliosaraiva@gmail.com)
* Special thanks to Raul Pereira da Silva (contato@raulpereira.com), Diego Plentz (http://plentz.org), Otavio Ribeiro 
Medeiros
