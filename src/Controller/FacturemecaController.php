<?php

namespace App\Controller;

use App\Entity\Facturemeca;
use App\Entity\Project;
use App\Form\FacturemecaType;
use App\Repository\FacturemecaRepository;
use Doctrine\ORM\EntityManagerInterface;
use Doctrine\Persistence\ManagerRegistry;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/facturemeca')]
class FacturemecaController extends AbstractController
{
    #[Route('/', name: 'app_facturemeca_index', methods: ['GET'])]
    public function index(FacturemecaRepository $facturemecaRepository): Response
    {
        return $this->render('facturemeca/index.html.twig', [
            'facturemecas' => $facturemecaRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_facturemeca_new', methods: ['GET', 'POST'])]
    public function new(Request $request, FacturemecaRepository $facturemecaRepository): Response
    {
        $facturemeca = new Facturemeca();
        $form = $this->createForm(FacturemecaType::class, $facturemeca);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $facturemecaRepository->save($facturemeca, true);

            return $this->redirectToRoute('app_facturemeca_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('facturemeca/new.html.twig', [
            'facturemeca' => $facturemeca,
            'form' => $form,
        ]);
    }

    #[Route('/{id_fact}', name: 'app_facturemeca_show', methods: ['GET'])]
    public function show(Facturemeca $facturemeca): Response
    {
        return $this->render('facturemeca/show.html.twig', [
            'facturemeca' => $facturemeca,
        ]);
    }

    #[Route('/{id_fact}/edit', name: 'app_facturemeca_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Facturemeca $facturemeca, FacturemecaRepository $facturemecaRepository): Response
    {
        $form = $this->createForm(FacturemecaType::class, $facturemeca);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $facturemecaRepository->save($facturemeca, true);

            return $this->redirectToRoute('app_facturemeca_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('facturemeca/edit.html.twig', [
            'facturemeca' => $facturemeca,
            'form' => $form,
        ]);
    }

    #[Route('/{id_fact}', name: 'app_facturemeca_delete', methods: ['POST'])]
    public function delete(Request $request, Facturemeca $facturemeca, FacturemecaRepository $facturemecaRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$facturemeca->getIdfact(), $request->request->get('_token'))) {
            $facturemecaRepository->remove($facturemeca, true);
        }

        return $this->redirectToRoute('app_facturemeca_index', [], Response::HTTP_SEE_OTHER);
    }


    #[Route('/pdf/{id_fact}', name: 'app_pdf', methods: ['GET'])]
    public function pdf2(Facturemeca $facture,ManagerRegistry $doctrine): Response
    {
          // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);



        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('projecttest/lista.html.twig', [

            'facture' => $facture,
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (inline view)
        $pdfOutput = $dompdf->output();
        return new Response($pdfOutput, 200, [
            'Content-Type' => 'application/pdf',
        ]);



}




}

