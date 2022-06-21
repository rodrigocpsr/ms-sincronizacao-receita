package br.com.desafio.sincronizacaoreceita;

import br.com.desafio.sincronizacaoreceita.model.ContaDTO;
import br.com.desafio.sincronizacaoreceita.service.CSVService;
import br.com.desafio.sincronizacaoreceita.uitl.MensagemUtil;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@Slf4j
@SpringBootApplication
public class SincronizacaoReceitaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SincronizacaoReceitaApplication.class, args);

		CSVService csvService = new CSVService();

		if (args.length > 0) {
			try {
				log.info("[FASE 1] - Lendo arquivo de entrada.csv");
				List<ContaDTO> lstContasDTO = csvService.lerCSV(args[0]);

				log.info("[FASE 2] - Processando arquivo de entrada.csv");
				csvService.processarCSV(lstContasDTO);

				log.info("[FASE 3] - Gerando arquivo de saida.csv");
				csvService.gerarCSV(lstContasDTO);
			} catch (IOException e) {
				log.error(e.getMessage());
			} catch (CsvRequiredFieldEmptyException e) {
				log.error(e.getMessage());
			} catch (CsvDataTypeMismatchException e) {
				log.error(e.getMessage());
			}
		} else {
			log.error(MensagemUtil.ARQUIVO_NAO_ENCONTRADO);
		}
	}

}
