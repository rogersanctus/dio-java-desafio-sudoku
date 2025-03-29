# Projeto para desafio dio.me - Trilha Java Básico

## O desafio:

### Criar um Jogo Sudoku

O jogo é feito em Java com a LibGDX.

Estão implementadas três telas:

* Inicial: para iniciar o jogo ou sair
* Atribuição de valores fixos: para atribuir valores fixos ao tabuleiro
* Jogo: o jogo em si, para completar todos os espaços do tabuleiro

## Como jogar

* Use o mouse para mover o seletor pelo tabuleiro
* Para atribuir um número use as teclas de 1 a 9 no teclado alfanumérico
* Para limpar uma atribuição, use a tecla 0 no teclado alfanumérico
* Para confirmar as atribuições na tela de valores fixos, use a tecla Enter

## Rodando o projeto:

Se não tiver o gradle instalado, execute o comando abaixo:

```bash
./gradlew clean && ./gradlew build
./gradlew run
```

Caso esteja em ambiente Windows, adicione a extensão `.bat` ao comando:

```bash
./gradlew.bat clean && ./gradlew.bat build
./gradlew.bat run
```
