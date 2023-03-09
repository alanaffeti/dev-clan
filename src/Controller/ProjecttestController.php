<?php

namespace App\Controller;

use App\Entity\Accident;

use App\Entity\Facturemeca;
use App\Entity\Livraison;
use App\Entity\Project;
use App\Form\ProjectType;
use App\Repository\LivraisonRepository;
use App\Repository\ProjectRepository;
use App\Service\PdfService;
use App\Service\QrcodeService;
use Doctrine\Persistence\ManagerRegistry;
use Dompdf\Dompdf;
use Dompdf\Options;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;

#[Route('/projecttest')]
class ProjecttestController extends AbstractController
{

    #[Route('/back', name: 'admin_back')]
    public function backindex(): Response
    {
        return $this->render('back/index.html.twig');
    }



    #[Route('/', name: 'app_projecttest_index', methods: ['GET'])]
    public function index(ProjectRepository $projectRepository,PaginatorInterface $paginator, Request $request): Response
    {$project = $projectRepository->findAll();

        $project = $paginator->paginate(
            $project, /* query NOT result */
            $request->query->getInt('page', 1), /*page number*/
            3 /*limit per page*/
        );


        return $this->render('projecttest/index.html.twig', ['projects' => $project]);


    }

    #[Route('/new', name: 'app_projecttest_new', methods: ['GET', 'POST'])]
    public function new(Request $request, ProjectRepository $projectRepository,SluggerInterface $slugger): Response
    {
        $project = new Project();
        $form = $this->createForm(ProjectType::class, $project);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $uploadedFiles = $form->get('facture_rep')->getData();
            $project->setFactureRep($this->movefiles3($slugger,$uploadedFiles,$project));




            $projectRepository->save($project, true);

            return $this->redirectToRoute('app_projecttest_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('projecttest/new.html.twig', [
            'project' => $project,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_projecttest_show', methods: ['GET'])]
    public function show(Project $project): Response
    {
        return $this->render('projecttest/show.html.twig', [
            'project' => $project,
        ]);
    }


    #[Route('/{id}/edit', name: 'app_projecttest_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Project $project, ProjectRepository $projectRepository,SluggerInterface $slugger): Response
    {
        $form = $this->createForm(ProjectType::class, $project);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {


            $uploadedFiles = $form->get('facture_rep')->getData();
            $project->setFactureRep($this->movefiles3($slugger,$uploadedFiles,$project));


            $projectRepository->save($project, true);

            return $this->redirectToRoute('app_projecttest_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('projecttest/edit.html.twig', [
            'project' => $project,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_projecttest_delete', methods: ['POST'])]
    public function delete(Request $request, Project $project, ProjectRepository $projectRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$project->getId(), $request->request->get('_token'))) {
            $projectRepository->remove($project, true);
        }

        return $this->redirectToRoute('app_projecttest_index', [], Response::HTTP_SEE_OTHER);
    }



    private  function movefiles3(SluggerInterface $slugger,$uploadedFiles,Project $project ):string
    {
        $filePaths = [];

        foreach ($uploadedFiles as $file) {


            $originalfilename = pathinfo($project->getNumTel(), flags: PATHINFO_FILENAME);
            $safefilename = $slugger->slug($originalfilename);
            $newFilename = $safefilename.'-'.uniqid() . '.' . $file->guessExtension();

            $file->move($this->getParameter('personne1_directory'), $newFilename);
            $filePaths[] = $newFilename;





        }
        return implode(',',$filePaths);
    }

    #[Route('/list/{id}', name: 'app_list', methods: ['GET'])]
    public function projectpdf(Project $project,Facturemeca $facturemeca): Response
    {
        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);



        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('projecttest/lista.html.twig', [

            'project' => $project,
            'facture' =>$facturemeca,


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
