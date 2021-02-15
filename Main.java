import static java.lang.System.out;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static PrintStream printStream = new PrintStream(out);

    /**
     * Ex:
     *  BLUE, YELLOW, RED, BLUE, GREEN, GREEN
        GREEN, RED, YELLOW, BLUE, RED, YELLOW
        RED, YELLOW, GREEN, BLUE, GREEN, YELLOW
        YELLOW, RED, BLUE, RED, GREEN, RED
    */

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        printStream.println("Digite a quantidade de cubos: ");
        Integer numCubos = console.nextInt();

        printStream.println("Agora, para cada cubo, digite as cores separadas por vírgula e seguindo a ordem FRENTE, DIREITA, ATRÁS, ESQUERDA, CIMA, BAIXO:");
        printStream.println("Exemplo: AZUL, AMARELO, VERMELHO, AZUL, VERDE, VERDE");
        printStream.println("No exemplo:");
        printStream.println("Frente = AZUL");
        printStream.println("Direita = AMARELO");
        printStream.println("Atrás = VERMELHO");
        printStream.println("Esquerda = AZUL");
        printStream.println("Cima = VERDE");
        printStream.println("Baixo = VERDE");
        printStream.println("Cores iguais devem ter o mesmo nome, independente de maiúsculo/minúsculo. Espaços iniciais e finais serão excluídos.");

        console.nextLine();
        List<Cubo> cubos = new LinkedList<>();
        for (int i = 1; i <= numCubos; i++) {
            printStream.println("Cubo #" + i + ": ");
            String coresDigitadas = console.nextLine();
            String[] cores = coresDigitadas.split(",");
            cubos.add(new Cubo(cores[0].trim(), cores[1].trim(), cores[2].trim(), cores[3].trim(), cores[4].trim(), cores[5].trim()));
        }
        printStream.println("Ótimo, agora vamos analisar as soluções!");

        List<List<Cubo>> ordensDeCuboValidas = pegarSolucoes(cubos);

        Integer i = 0;
        for (List<Cubo> possibilidade : ordensDeCuboValidas) {
            i++;
            printStream.println("Possibilidade #" + i + ":");
            Integer cuboNum = 0;
            // Por causa da recursão para gerar as possibilidades, precisamos inverter a ordem dos cubos
            for (int j = possibilidade.size() - 1; j >= 0; j--) {
                cuboNum++;
                printStream.print("Cubo #" + cuboNum + ": ");
                possibilidade.get(j).printCubo();
            }
            printStream.println("--------------------------------------------------------------------------------------------------------------------");

        }

        if (ordensDeCuboValidas.isEmpty()) {
            printStream.println("Nenhuma configuração válida foi encontrada para esse conjunto de cubos.");
        } else {
            printStream.println("Essas foram todas as configurações válidas encontradas para esse conjunto de cubos.");
        }

        printStream.println("Pressione ENTER para sair.");
        console.nextLine();
        console.close();
    }

    private static <T> List<List<T>> pegarCombinacoesEntreListas(List<List<T>> listaDeListas, int i) {
        // Caso a gente já tenha chegado no tamanho da lista, essa iteração já chegou no fim
        if(i == listaDeListas.size()) {
            List<List<T>> resultado = new LinkedList<>();
            resultado.add(new LinkedList<>());
            return resultado;
        }
        
        List<List<T>> resultado = new LinkedList<>();
        List<List<T>> recursao = pegarCombinacoesEntreListas(listaDeListas, i+1); // chamada recursiva pra próxima iteração
        
        // para cada elemento da lisa de listas
        for(int j = 0; j < listaDeListas.get(i).size(); j++) {
            // pegamos todas as combinações do resto das listas
            for(int k = 0; k < recursao.size(); k++) {
                // pega a combinação da recursão
                List<T> novaLista = new LinkedList<>();
                for(T integer : recursao.get(k)) {
                    novaLista.add(integer);
                }
                // adiciona um elemento novo da lista principal
                novaLista.add(listaDeListas.get(i).get(j));
                // adiciona mais uma combinação ao resultado
                resultado.add(novaLista);
            }
        }
        return resultado;
    }

    private static List<List<Cubo>> pegarSolucoes(List<Cubo> cubos) {
        List<List<Cubo>> todasPosicoesDosCubos = new LinkedList<>();
        List<List<Cubo>> combinacoesValidas = new LinkedList<>();

        for (Cubo cubo : cubos) {
            todasPosicoesDosCubos.add(pegarPosicoesPossiveisParaCubo(cubo));
        }

        List<List<Cubo>> possiveisCombinacoes = pegarCombinacoesEntreListas(todasPosicoesDosCubos, 0);
        for (List<Cubo> possivelCombinacao : possiveisCombinacoes) {
            if (estadoValido(possivelCombinacao)){
                combinacoesValidas.add(possivelCombinacao);
            }
        }

        return combinacoesValidas;
    }

    private static boolean estadoValido(List<Cubo> cubos) {
        Set<String> coresFrente = new HashSet<>();
        Set<String> coresTras = new HashSet<>();
        Set<String> coresDireita = new HashSet<>();
        Set<String> coresEsquerda = new HashSet<>();
        for (Cubo cubo : cubos) {
            if (checaSeCorEstaPresenteEAdicionaCasoNao(coresFrente, cubo.frente) 
                || checaSeCorEstaPresenteEAdicionaCasoNao(coresTras, cubo.atras)
                || checaSeCorEstaPresenteEAdicionaCasoNao(coresDireita, cubo.direita)
                || checaSeCorEstaPresenteEAdicionaCasoNao(coresEsquerda, cubo.esquerda)) {
                    return false;
                }
            
        }
        return true;
    }

    private static boolean checaSeCorEstaPresenteEAdicionaCasoNao(Set<String> cores, String cor){
        if (cores.contains(cor.toLowerCase())) {
            return true;
        } else {
            cores.add(cor.toLowerCase());
            return false;
        }
    }

    private static List<Cubo> pegarPosicoesPossiveisParaCubo(Cubo cubo) {
        List<Cubo> cubos = new LinkedList<>();

        for (int i = 0; i<4; i++) {
            for (int j = 0; j<4; j++) {
                cubo = cubo.girarSentidoHorario();
                cubos.add(cubo);
            }
            cubo = cubo.girarParaTras();
        }
          
        cubo = cubo.girarSentidoHorario();
        cubo = cubo.girarParaTras();

        for (int i = 0; i<4; i++) {
            cubo = cubo.girarSentidoHorario();
            cubos.add(cubo);
        }

        cubo = cubo.girarParaTras();
        cubo = cubo.girarParaTras();

        for (int i = 0; i<4; i++) {
            cubo = cubo.girarSentidoHorario();
            cubos.add(cubo);
        }

        return cubos;
    }

    static class Cubo {
        String frente;
        String atras;
        String direita;
        String esquerda;
        String cima;
        String baixo;
        List<String> ordemDeGiros = new LinkedList<>();

        Cubo(){}

        Cubo(String frente, String direita, String atras, String esquerda, String cima, String baixo){
            this.frente = frente;
            this.atras = atras;
            this.direita = direita;
            this.esquerda = esquerda;
            this.cima = cima;
            this.baixo = baixo;
        }

        public Cubo girarSentidoHorario() {
            Cubo novoCubo = new Cubo();
            novoCubo.frente = direita;
            novoCubo.direita = atras;
            novoCubo.atras = esquerda;
            novoCubo.esquerda = frente;
            novoCubo.cima = cima;
            novoCubo.baixo = baixo;

            novoCubo.ordemDeGiros = new LinkedList<>(ordemDeGiros);
            novoCubo.ordemDeGiros.add("Horário");
            return novoCubo;
        }

        public Cubo girarParaTras() {
            Cubo novoCubo = new Cubo();
            novoCubo.frente = baixo;
            novoCubo.baixo = atras;
            novoCubo.atras = cima;
            novoCubo.cima = frente;
            novoCubo.esquerda = esquerda;
            novoCubo.direita = direita;

            novoCubo.ordemDeGiros = new LinkedList<>(ordemDeGiros);
            novoCubo.ordemDeGiros.add("Tras");
            return novoCubo;
        }

        public void printCubo() {
            Map<String, String> dicionarioCubo = new LinkedHashMap<>();
            dicionarioCubo.put("frente", frente);
            dicionarioCubo.put("direita", direita);
            dicionarioCubo.put("atrás", atras);
            dicionarioCubo.put("esquerda", esquerda);
            dicionarioCubo.put("cima", cima);
            dicionarioCubo.put("baixo", baixo);
            printStream.println(dicionarioCubo.toString());
        }

        public void printCuboComGiros() {
            Map<String, String> dicionarioCubo = new LinkedHashMap<>();
            dicionarioCubo.put("frente", frente);
            dicionarioCubo.put("direita", direita);
            dicionarioCubo.put("atrás", atras);
            dicionarioCubo.put("esquerda", esquerda);
            dicionarioCubo.put("cima", cima);
            dicionarioCubo.put("baixo", baixo);
            printStream.println(dicionarioCubo.toString() + " -- " + ordemDeGiros.toString());
        }
    }
}