# Insanidade Instantanea

Código em Java para a solução do Puzzle de Insanidade Instantânea utilizando backtracking. Em resumo, todas as possibilidades de combinação dos cubos são formadas e analisadas, a fim de achar as combinações válidas.

## Entrada
Deve-se mandar como entrada:
* Nº de Cubos
* Cores dos cubos separados por vírgula, seguindo a ordem FRENTE, DIREITA, ATRÁS, ESQUERDA, CIMA, BAIXO
* Exemplo:
    * 4
    * BLUE, YELLOW, RED, BLUE, GREEN, GREEN
    * GREEN, RED, YELLOW, BLUE, RED, YELLOW
    * RED, YELLOW, GREEN, BLUE, GREEN, YELLOW
    * YELLOW, RED, BLUE, RED, GREEN, RED
* Espaços iniciais, finais e maiúsculo/minúsculo são ignorados. Palavras diferentes serão tratadas como cores diferentes.

## Saída
* Caso deseje apenas saber todas as possiblidades, na linha 60 do código usar "printarCubo()". Isso trará todas as possibilidades daquela configuração. Opção setada como default.
* Caso deseje saber as possibilidades com a ordem em que o cubo foi girado pelo algoritmo, na linha 60 do código usar "printCuboComGiros()".