package br.com.desafio.sincronizacaoreceita.service;

import br.com.desafio.sincronizacaoreceita.model.ContaDTO;
import br.com.desafio.sincronizacaoreceita.uitl.MensagemUtil;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Classe responsável pela regra de negócio
 * relacionados a arquivos .csv
 * (leitura, processamento e geração)
 */
@Slf4j
@Service
public class CSVService {

    @Autowired
    private ReceitaService receitaService;

    /**
     * Método responsável por ler o arquivo .csv de entrada
     *
     * @param caminhoArquivoCSV
     * @return List<ContaEntrada>
     * @throws IOException
     */
    public List<ContaDTO> lerCSV(String caminhoArquivoCSV) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(caminhoArquivoCSV));

        CsvToBean<ContaDTO> csvToBean = new CsvToBeanBuilder(reader)
                .withType(ContaDTO.class)
                .withSeparator(';')
                .build();

        List<ContaDTO> contas = csvToBean.parse();

        return contas;
    }

    /**
     * Método responsável por processar o arquivo entrada.csv
     *
     * @param lstContasDTO
     * @return List<ContaDTO>
     */
    public List<ContaDTO> processarCSV(List<ContaDTO> lstContasDTO) {
        lstContasDTO.parallelStream().forEach(c -> {
            boolean resultado;

            try {
                resultado = receitaService.atualizarConta(
                        c.getAgencia() != null ? c.getAgencia() : null,
                        c.getConta() != null ? c.getConta().replace("-", "") : null,
                        c.getSaldo() != null ? Double.parseDouble(c.getSaldo().replace(",", ".")) : null,
                        c.getStatus() != null ? c.getStatus() : null);
            } catch (RuntimeException e) {
                resultado = false;
                System.out.println(e);
            } catch (InterruptedException e) {
                resultado = false;
                System.out.println(e);
            }

            c.setResultado(resultado);
        });

        return lstContasDTO;
    }

    /**
     * Método responsável por gerar o arquivo saida.csv
     *
     * @param lstContaDTO
     */
    public void gerarCSV(List<ContaDTO> lstContaDTO) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        Writer writer = Files.newBufferedWriter(Paths.get("saida.csv"));
        StatefulBeanToCsv<ContaDTO> beanToCsv = new StatefulBeanToCsvBuilder(writer).withSeparator(';').build();

        beanToCsv.write(lstContaDTO);
        writer.flush();
        writer.close();

        log.error(MensagemUtil.ARQUIVO_GERADO_SUCESSO);
    }

}
